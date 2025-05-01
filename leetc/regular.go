package leetc

func IsMatch(s string, p string) bool {
	ls, lp := len(s), len(p)
	var prev uint8
	for i, j := 0, 0; i < ls || j < lp; {
		// + ? ( ) /d /s /w /D /S /W
		if j >= lp {
			return false
		} else if i >= ls {
			if p[j] == '*' && j+1 < lp {
				j += 1
			}
			for ; j+2 < lp && p[j+1] == '*'; j += 2 {
			}
			if j == lp-1 {
				return p[j] == '*' || p[j] == '.' || (p[j] == s[i-1] && p[j-1] == '*')
			} else if j == lp-2 {
				return p[j+1] == '*' || p[j+1] == '.' || p[j+1] == s[i-1]
			}
			return false
		}
		if p[j] == '*' {
			if prev != '.' && s[i] != prev {
				j++
				i--
			} else if j+2 < lp && p[j+1] == prev && p[j+2] == '*' {
				j += 2
				i--
			}
			i++
		} else if p[j] == '.' {
			prev = p[j]
			i++ //. == any current s[i]
			j++
		} else {
			prev = p[j]
			if p[j] != s[i] {
				if j+1 < lp && p[j+1] == '*' {
					i -= 1
					j += 1
				} else {
					return false
				}
			}
			i++
			j++
		}
	}
	return true
}
