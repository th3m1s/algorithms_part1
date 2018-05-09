#make

TARGETS = target

MAKE = make

FINDBUGS_ALGS4 = findbugs-algs4
PMDS_ALGS4 = pmd-algs4
CHECKSTYLES_ALGS4 = checkstyle-algs4

FINDBUGS_COURSERA = findbugs-coursera
PMD_COURSERA = pmd-coursera
CHECKSTYLE_COURSERA = checkstyle-coursera



# ==================================================
all:	${TARGETS}


# ==================================================
target:	coursera


# == utils ========================================
compile:
	ehco "test"



coursera:
	${FINDBUGS_ALGS4} *.class
	pmd-coursera .
	checkstyle-coursera *.java

algs4:
	findbugs-algs4 *.class
	pmd-algs4 .
	checkstyle-algs4 *.java


format:
	java -jar ~/git/algorithms_part1/env/google-java-format-1.5-all-deps.jar *.java

format-replace:
	java -jar ~/git/algorithms_part1/env/google-java-format-1.5-all-deps.jar --replace *.java

# The formatter can act on whole files, on limited lines (--lines), on specific offsets (--offset), passing through to standard-out (default) or altered in-place (--replace).


# == utils ========================================
clean:
	${RM} *~



# == setup ========================================
setup:
	eval ./env/statup.sh
