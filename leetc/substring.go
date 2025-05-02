package leetc

func FindSubstring(s string, words []string) []int {
	var result []int
	var sl, wl, ws = len(s), len(words), 0
	if sl == 0 || wl == 0 {
		return result
	}

	var head *IndexInt
	for i := 0; i < wl; i++ {
		if ws == 0 {
			ws = len(words[i])
		}
		for _, v := range indexes(s, words[i]) {
			var prev *IndexInt
			var cur = head
			if head == nil {
				head = &IndexInt{Val: v, Index: i}
				cur = head
				continue
			}
			for cur != nil && v > cur.Val {
				prev = cur
				cur = cur.Next
			}
			if prev == nil {
				head = &IndexInt{Val: v, Index: i, Next: cur}
			} else {
				prev.Next = &IndexInt{Val: v, Index: i, Next: cur}
			}
		}
	}

	for cur := head; cur != nil; cur = cur.Next {
		var nextVal = cur.Val + ws
		var notIndexes []int
		notIndexes = append(notIndexes, cur.Index)

		if len(notIndexes) == wl {
			result = append(result, cur.Val)
			continue
		}

		for cursor := cur.Next; cursor != nil; cursor = cursor.Next {
			if nextVal != cursor.Val || isInArray(cursor.Index, notIndexes) {
				continue
			}
			nextVal += ws
			notIndexes = append(notIndexes, cursor.Index)
			if len(notIndexes) == wl {
				if !isInArray(cur.Val, result) {
					result = append(result, cur.Val)
				}
				break
			}
		}
	}

	return result
}
