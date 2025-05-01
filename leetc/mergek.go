package leetc

func MergeKLists(lists []*ListNode) *ListNode {
	var cur = 10001
	var resultChain *ListNode
	var idx = 0
	for i := 0; i < len(lists); i++ {
		var curNode = lists[i]
		if curNode != nil && curNode.Val < cur {
			cur = curNode.Val
			resultChain = curNode
			idx = i
		}
	}

	for i := 0; i < len(lists); i++ {
		if i == idx {
			continue // 跳过选好的结果链
		}
		var resultNode = resultChain
		var curNode = lists[i]
		for curNode != nil && resultNode != nil && resultNode.Next != nil {
			if curNode.Val < resultNode.Next.Val {
				var resultNext = resultNode.Next
				var curNext = curNode.Next
				resultNode.Next = curNode
				curNode.Next = resultNext
				curNode = curNext
			} else {
				resultNode = resultNode.Next
			}
		}
		if curNode != nil {
			resultNode.Next = curNode
		}
	}

	return resultChain
}
