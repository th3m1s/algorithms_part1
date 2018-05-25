#!/bin/bash
## reference https://algs4.cs.princeton.edu/linux/

echo "setup coursera java environment"

ALGS4ROOT=`pwd`

mkdir env
cd env
echo "Download google-java-formater"
if [ -e google-java-format-1.5-all-deps.jar ]
then
    echo "google-java-format-1.5-all-deps.jar xist"
else
    wget https://github.com/google/google-java-format/releases/download/google-java-format-1.5/google-java-format-1.5-all-deps.jar
fi

mkdir algs4
cd algs4
# Download the textbook libraries from algs4.jar and the Java wrapper scripts from javac-algs4, javac-cos226, javac-coursera, java-algs4, java-cos226, and java-coursera.
if [ -e algs4.jar ]
then
    echo "algs4.jar exist"
else
    curl -O "https://algs4.cs.princeton.edu/code/algs4.jar"
fi


# Download DrJava from drjava.jar, the wrapper script from drjava, and the configuration file from .drjava.
if [ -e drjava.jar ]
then
    echo "drjava exist"
else
    curl -O "https://algs4.cs.princeton.edu/linux/drjava.jar"
fi

# Download Findbugs 3.0.1 from findbugs.zip; our Findbugs configuration file from findbugs.xml; and the Findbugs wrapper scripts from findbugs-algs4, findbugs-cos226, and findbugs-coursera.
if [ -e findbugs-* ]
then
    echo "findbugs exist"
else
    curl -O "https://algs4.cs.princeton.edu/linux/findbugs.{zip,xml}"
    # curl -O "https://algs4.cs.princeton.edu/linux/findbugs-{algs4,cos226,coursera}"
    unzip findbugs.zip
fi


# Download PMD 5.8.1 from pmd.zip; our PMD configuration file from pmd.xml and the PMD wrapper scripts pmd-algs4, pmd-cos226, and pmd-coursera.

if [ -e pmd.* ]
then
    echo "pmd exist"
else
    curl -O "https://algs4.cs.princeton.edu/linux/pmd.{zip,xml}"
    # curl -O "https://algs4.cs.princeton.edu/linux/pmd-{algs4,cos226,coursera}"
    unzip pmd.zip
fi



# Download Checkstyle 8.2 from checkstyle.zip; our Checkstyle configuration files from checkstyle-algs4.xml, checkstyle-cos226.xml, and checkstyle-coursera.xml; and the Checkstyle wrapper scripts from checkstyle-algs4, checkstyle-cos226, and checkstyle-coursera.
if [ -e checkstyle-suppressions.xml ]
then
    echo "checkstyle exist"
else
    curl -O "https://algs4.cs.princeton.edu/linux/checkstyle.zip"
    curl -O "https://algs4.cs.princeton.edu/linux/checkstyle-suppressions.xml"
    curl -O "https://algs4.cs.princeton.edu/linux/checkstyle-{algs4,cos226,coursera}.xml"
    # curl -O "https://algs4.cs.princeton.edu/linux/checkstyle-{algs4,cos226,coursera}"
    unzip checkstyle.zip
fi

rm *.zip

cd ..

mkdir -p ~/usr/local
unlink ~/usr/local/algs4
ln -s $ALGS4ROOT/env/algs4 ~/usr/local/

mkdir -p ~/usr/bin

unlink ~/usr/bin/drjava
ls -d ~/usr/bin/* |grep -e algs4 -e cos226 -e coursera |xargs -n1 unlink
ln -s $ALGS4ROOT/env/bin/* ~/usr/bin/

unlink ~/.drjava
ln -s $ALGS4ROOT/env/.drjava ~
