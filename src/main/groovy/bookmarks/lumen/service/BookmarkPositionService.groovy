package bookmarks.lumen.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable

import bookmarks.lumen.data.SequenceRepository
import bookmarks.lumen.data.BookmarkPositionRepository
import bookmarks.lumen.domain.BookmarkPosition
import bookmarks.lumen.domain.SequenceNumber

@Service
class BookmarkPositionService {
	@Autowired private SequenceRepository seqRepository
	
	List<BookmarkPosition> moveInArray(List<BookmarkPosition> all, Long prevIndex, Long newIndex) {
		def updated = []
		if (newIndex == all.size() - 1) {
			Long updateIndex = all[newIndex].getIndex() + 1
			if (needsReset(updateIndex, updateIndex)) {
				BookmarkPosition b = all.removeAt((int)prevIndex)
				all.add((int) newIndex, b)
				return reset(all)
			}
			setIndex(all[prevIndex], updateIndex)
			updated = [all[prevIndex]]
		} else {
			def curr = prevIndex > newIndex ? newIndex : newIndex + 1
			Long startIndex = all[curr].getIndex()
			setIndex(all[prevIndex], startIndex)
			updated.add(all[prevIndex])
			while ((curr + 1 < all.size()) && (all[curr].getIndex() + 1 == all[curr + 1].getIndex())) {
				setIndex(all[curr], all[curr].getIndex() + 1)
				updated.add(all[curr])
				curr++
			}
			Long endIndex = all[curr].getIndex() + 1
			if (needsReset(startIndex, endIndex)) {
				BookmarkPosition b = all.removeAt((int) prevIndex)
				all.add((int) newIndex, b)
				return reset(all)
			}
			setIndex(all[curr], endIndex)
		}
		return updated
	}	
	
	private setIndex(BookmarkPosition bPos, Long index) {
		if (!(index % BookmarkPositionRepository.INCREMENT_BY)) {
			
		}
		SequenceNumber newNum = seqRepository.save(new SequenceNumber(index))
		bPos.setPosition(newNum)
	}
	
	private needsReset(Long startIndex, Long endIndex) {
		if (!(startIndex % BookmarkPositionRepository.INCREMENT_BY)) {
			return true
		}
		Long next = startIndex / BookmarkPositionRepository.INCREMENT_BY * BookmarkPositionRepository.INCREMENT_BY
		 + BookmarkPositionRepository.INCREMENT_BY
		return endIndex >= next
	}
	
	private reset(List<BookmarkPosition> all) {
		for (BookmarkPosition b : all) {
			SequenceNumber num = new SequenceNumber()
			seqRepository.save(num)
			b.setPosition(num)
		}
		return all
	}
}