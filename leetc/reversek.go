package leetc

func ReverseKGroup(head *ListNode, k int) *ListNode {
	if k < 2 || head == nil || head.Next == nil {
		return head
	}
	var result, last, prev, left *ListNode
	var cur = head
	list := make([]*ListNode, k)
	for i := 0; cur != nil; i++ {
		list[i] = cur
		cur = cur.Next
		if i == k-1 {
			i = -1
			for j := 0; j < k; j++ {
				list[j].Next = prev
				prev = list[j]
			}
			if result == nil {
				result = list[k-1]
			} else {
				last.Next = list[k-1]
			}
			last = list[0]
			left = cur
			prev = nil
		}
	}
	last.Next = left
	return result
}

//func ReverseKGroup(head *ListNode, k int) *ListNode {
//	if k < 2 || head == nil || head.Next == nil {
//		return head
//	}
//	//result,     last,  left
//	//    |--------|------|--------|
//	var result, last, first, left *ListNode
//	var cur = head
//	var prev *ListNode = nil
//	for i := k - 1; cur != nil; i-- {
//		curNext := cur.Next
//		cur.Next = prev
//		if prev == nil {
//			first = cur
//		}
//		prev = cur
//		cur = curNext
//		if i == 0 { //reverse
//			i = k
//			if result == nil {
//				result = prev //
//			} else {
//				last.Next = prev
//			}
//			last = first
//			left = cur
//			prev = nil
//		}
//	}
//	if prev != nil {
//		last.Next = left
//	}
//	return result
//}
