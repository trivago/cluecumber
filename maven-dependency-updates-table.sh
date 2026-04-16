#!/bin/sh
# Reads stdout from: mvn versions:display-dependency-updates versions:display-plugin-updates
# Writes grouped tables: absolute pom path, then Dependency / Old / New columns.
# Usage: ... | maven-dependency-updates-table.sh <absolute-project-root>
set -eu
root=${1:?absolute project root (e.g. CURDIR) required}
tab=$(printf '\t')

awk -v "root=$root" '
/^\[INFO\][[:space:]]+from[[:space:]]+/ {
	sub(/^\[INFO\][[:space:]]+from[[:space:]]+/, "")
	gsub(/[[:space:]]+$/, "")
	pom = $0
	next
}
/^\[INFO\].* -> / {
	if (pom == "") {
		next
	}
	line = $0
	sub(/^[[:space:]]*\[INFO\][[:space:]]+/, "", line)
	if (index(line, " -> ") == 0) {
		next
	}
	n = index(line, " -> ")
	newv = substr(line, n + 4)
	gsub(/[[:space:]]+$/, "", newv)
	temp = substr(line, 1, n - 1)
	if (match(temp, / ([^ ]+)$/)) {
		old = substr(temp, RSTART + 1)
		dep = substr(temp, 1, RSTART - 1)
		gsub(/[[:space:].]+$/, "", dep)
	} else {
		next
	}
	if (tolower(newv) ~ /beta/) {
		next
	}
	if (newv ~ /-M[0-9]+/) {
		next
	}
	fullpom = pom
	if (pom !~ /^\//) {
		fullpom = root "/" pom
	}
	print fullpom "\t" dep "\t" old "\t" newv
}
' | sort -t "$tab" -k1,1 -k2,2 | awk -F "$tab" '
function repeat(c, n,   r, i) {
	r = ""
	for (i = 0; i < n; i++) {
		r = r c
	}
	return r
}
function flush(   i, w1, w2, w3, h1, h2, h3) {
	if (n == 0) {
		return
	}
	h1 = "Dependency"
	h2 = "Old"
	h3 = "New"
	w1 = length(h1)
	w2 = length(h2)
	w3 = length(h3)
	for (i = 1; i <= n; i++) {
		if (length(dep[i]) > w1) {
			w1 = length(dep[i])
		}
		if (length(old[i]) > w2) {
			w2 = length(old[i])
		}
		if (length(newv[i]) > w3) {
			w3 = length(newv[i])
		}
	}
	printf "%-*s  %-*s  %s\n", w1, h1, w2, h2, h3
	printf "%-*s  %-*s  %s\n", w1, repeat("-", w1), w2, repeat("-", w2), repeat("-", w3)
	for (i = 1; i <= n; i++) {
		printf "%-*s  %-*s  %s\n", w1, dep[i], w2, old[i], newv[i]
	}
}
BEGIN {
	prev = ""
	n = 0
}
{
	if ($1 != prev) {
		if (prev != "") {
			flush()
			print ""
		}
		print $1 ":"
		prev = $1
		n = 0
	}
	n++
	dep[n] = $2
	old[n] = $3
	newv[n] = $4
}
END {
	if (prev != "") {
		flush()
	}
}
'
