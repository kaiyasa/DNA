#!/bin/bash

# Allows for running a grammar via the TestRig tool in antlr
#
#  typeical usage:
#    echo 'ssample code' | ./gtest -tokens
#
#    echo 'ssample code' | env RULE=subRule ./gtest -tokens
#
#  prolonged sub rule testing:
#    $ RULE=someRule
#    $ ./gtest src/test/manual/sample1.txt -tree
#    $ ./gtest src/test/manual/sample2.txt -tree
#


function tree_print() {
    chars=()
    while read -N 1 c; do
        chars+=("$c")
    done

    close=false
    for c in "${chars[@]}"; do
        case "$c" in
          '(') echo -en "\n$indent"; echo -n "$c"
               indent="  $indent"
               echo -en "\n$indent"
               close=false
               ;;
          ')') indent="${indent:2}"
               echo -en "\n$indent"; echo -n "$c"
               close=true
               ;;
            *) if $close; then
                   echo -en "\n$indent"
                   [ "$c" == " " ] || echo -n "$c"
                   close=false
               else
                   echo -n "$c"
               fi
               ;;
        esac
    done
    echo
}


case $(uname -o) in
  Cygwin) SEP=';' ;;
       *) SEP=':'
esac

case "$1" in
  -c) shift;
      RULE=$(basename $(dirname "$1"))
      ;;
  *) ;;
esac

#
# ANTLR_JAR points to a "complete" jar for the testrig and tools
#

BUILD=target/classes
  java \
    -cp "${ANTLR_JAR}${SEP}${BUILD}${SEP}}${CLASSPATH}" \
    org.antlr.v4.gui.TestRig \
    ${GRAMMAR:=dna.antlr.Dna} \
    ${RULE:=xUnit} \
    "$@" | tree_print
