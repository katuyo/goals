package leetc

func FindMedianSortedArrays(nums1 []int, nums2 []int) float64 {
	idx, l1, l2 := 0, len(nums1), len(nums2)
	if (l1+l2)%2 == 1 {
		idx = (l1 + l2 - 1) / 2
	} else {
		// mid = (nums[idx-1], nums[idx]) / 2
		idx = (l1 + l2) / 2
	}
	var nums = make([]int, idx+1)

	var cur = 0
	for i, j := 0, 0; i < l1 || j < l2; {
		if cur == idx+1 {
			break
		}
		if i >= l1 {
			nums[cur] = nums2[j]
			j++
		} else if j >= l2 {
			nums[cur] = nums1[i]
			i++
		} else if nums1[i] < nums2[j] {
			nums[cur] = nums1[i]
			i++
		} else {
			nums[cur] = nums2[j]
			j++
		}
		cur++
	}

	if (l1+l2)%2 == 1 {
		return float64(nums[idx])
	} else {
		return (float64(nums[idx]) + float64(nums[idx-1])) / 2
	}
}
