package leetc

func contains(s string, word string) bool {
	return index(s, word) != -1
}

func isInArray(num int, nums []int) bool {
	for i := 0; i < len(nums); i++ {
		if nums[i] == num {
			return true
		}
	}
	return false
}

func index(s string, word string) int {
	if len(s) == 0 || len(word) == 0 {
		return -1
	}
	sl := len(s)
	for i := 0; i < sl; i++ {
		if s[i] == word[0] {
			var pass = true
			for j := 0; j < len(word); j++ {
				if i+j >= sl || s[i+j] != word[j] {
					pass = false
					break
				}
			}
			if pass {
				return i
			}
		}
	}
	return -1
}

func indexes(s string, word string) []int {
	wl := len(word)
	var result []int
	var last = -1
	for dropped := 0; len(s) >= wl; s, dropped = s[1:], dropped+1 {
		i := index(s, word)
		if i == -1 {
			break
		}
		if last != dropped+i {
			result = append(result, dropped+i)
			last = dropped + i
		}
	}
	return result
}

func quickSort(v []int, s, e int) {
	pivot := v[s]
	p, i, j := s, s, e

	for i <= j {
		for j >= p && v[j] >= pivot {
			j--
		}
		if j >= p {
			v[p] = v[j]
			p = j
		}
		for i <= p && v[i] <= pivot {
			i++
		}
		if i <= p {
			v[p] = v[i]
			p = i
		}
	}
	v[p] = pivot
	if p-s > 1 {
		quickSort(v, s, p-1)
	}
	if e-p > 1 {
		quickSort(v, p+1, e)
	}
}

func sortQuick(nums []int) {
	if len(nums) <= 1 {
		return
	}
	quickSort(nums, 0, len(nums)-1)
}

func minStringUnit(s string) string {
	if len(s) < 2 {
		return s
	}
	l := len(s)
	for i := 1; i <= l/2; i++ {
		if l%i > 0 {
			continue
		}
		var pass = true
		for j := 0; j+i+i <= l; j += i {
			if s[j:j+i] != s[j+i:j+i+i] {
				pass = false
				break
			}
		}
		if pass {
			return s[0:i]
		}
	}
	return s
}
