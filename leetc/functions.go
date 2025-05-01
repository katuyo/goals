package leetc

func index(s string, word string) int {
	if len(s) == 0 || len(word) == 0 {
		return -1
	}
	sl := len(s)
	for i := 0; i < sl; i++ {
		if s[i] == word[0] {
			var pass = true
			for j := 0; j < len(word) && i+j < sl; j++ {
				if s[i+j] != word[j] {
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
	for len(s) >= wl {
		i := index(s, word)
		if i == -1 {
			break
		}
		result = append(result, index(s, word))
		s = s[1:]
	}
	return result
}

func sortQuick(nums []int) {
	if len(nums) <= 1 {
		return
	}
	quickSort(nums, 0, len(nums)-1)
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
