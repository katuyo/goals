package leetc

func FindSubstring(s string, words []string) []int {
	var result []int
	if len(s) == 0 || len(words) == 0 {
		return result
	}

	wl := len(words)
	var l, rplc = 0, "" // rpcl is * string of l length, to replace the found word in s.
	var base = 0

	for len(s) >= wl*l {
		var minium = -1
		var indexes = make([]int, wl)
		for i, cp := 0, s; i < wl; i++ {
			if l == 0 {
				l = len(words[i])
				for j := 0; j < l; j++ {
					rplc += "*"
				}
			}
			idx := index(cp, words[i])
			if idx+l > len(cp) {
				break
			}
			if idx >= 0 {
				cp = cp[0:idx] + rplc + cp[idx+l:]
				indexes[i] = idx
			}
			if idx < minium || minium == -1 {
				minium = idx
			}
		}
		if minium == -1 {
			break
		}
		sortQuick(indexes)
		pass := true
		for i := 0; i < wl-1; i++ {
			if indexes[i+1]-indexes[i] != l {
				pass = false
				break
			}
		}
		if pass {
			result = append(result, base+minium)
		}
		//drop := len(minStringUnit(words[wi]))
		s = s[1:]
		base += 1 // the length of the cut s former.
	}
	return result
}
