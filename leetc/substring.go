package leetc

func FindSubstring(s string, words []string) []int {
	var result []int
	if len(s) == 0 || len(words) == 0 {
		return result
	}
	var wl, l = len(words), 0

	var wordsIndexes = make([][]int, wl)
	for i := 0; i < wl; i++ {
		if l == 0 {
			l = len(words[i])
		}
		idxs := indexes(s, words[i])
		wordsIndexes[i] = idxs
	}

	var loops = make([]int, wl)
	var temp = make([]int, wl)

	for all0 := false; !all0; {
		var next = true
		all0 = true
		for wordIdx := 0; wordIdx < wl; wordIdx++ { //每个里找一个
			temp[wordIdx] = wordsIndexes[wordIdx][loops[wordIdx]]
			if next {
				loops[wordIdx]++
				next = false
			}
			if loops[wordIdx] >= len(wordsIndexes[wordIdx]) {
				loops[wordIdx] = 0
				next = true
			}
			if loops[wordIdx] != 0 {
				all0 = false
			}
		}
		sortQuick(temp)
		pass := true
		for i := 1; i < wl; i++ {
			if temp[i]-temp[i-1] != l {
				pass = false
				break
			}
		}
		if pass && !isInArray(temp[0], result) {
			result = append(result, temp[0])
		}
	}
	return result
}
