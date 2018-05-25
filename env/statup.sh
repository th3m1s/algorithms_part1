#!/bin/bash
## reference https://algs4.cs.princeton.edu/linux/

echo "setup coursera java environment"

ALGS4ROOT=`pwd`

mkdir env
cd env
echo "Download google-java-formater"
wget https://github.com/google/google-java-format/releases/download/google-java-format-1.5/google-java-format-1.5-all-deps.jar

mkdir algs4
cd algs4
# Download the textbook libraries from algs4.jar and the Java wrapper scripts from javac-algs4, javac-cos226, javac-coursera, java-algs4, java-cos226, and java-coursera.
curl -O "https://algs4.cs.princeton.edu/code/algs4.jar"


# Download DrJava from drjava.jar, the wrapper script from drjava, and the configuration file from .drjava.
curl -O "https://algs4.cs.princeton.edu/linux/{drjava.jar,drjava}"
chmod 755 drjava
ln -s $ALGS4ROOT/env/.drjava ~

# Download Findbugs 3.0.1 from findbugs.zip; our Findbugs configuration file from findbugs.xml; and the Findbugs wrapper scripts from findbugs-algs4, findbugs-cos226, and findbugs-coursera.
curl -O "https://algs4.cs.princeton.edu/linux/findbugs.{zip,xml}"
# curl -O "https://algs4.cs.princeton.edu/linux/findbugs-{algs4,cos226,coursera}"
unzip findbugs.zip


# Download PMD 5.8.1 from pmd.zip; our PMD configuration file from pmd.xml and the PMD wrapper scripts pmd-algs4, pmd-cos226, and pmd-coursera.
curl -O "https://algs4.cs.princeton.edu/linux/pmd.{zip,xml}"
# curl -O "https://algs4.cs.princeton.edu/linux/pmd-{algs4,cos226,coursera}"
unzip pmd.zip



# Download Checkstyle 8.2 from checkstyle.zip; our Checkstyle configuration files from checkstyle-algs4.xml, checkstyle-cos226.xml, and checkstyle-coursera.xml; and the Checkstyle wrapper scripts from checkstyle-algs4, checkstyle-cos226, and checkstyle-coursera.
curl -O "https://algs4.cs.princeton.edu/linux/checkstyle.zip"
curl -O "https://algs4.cs.princeton.edu/linux/checkstyle-suppressions.xml"
curl -O "https://algs4.cs.princeton.edu/linux/checkstyle-{algs4,cos226,coursera}.xml"
# curl -O "https://algs4.cs.princeton.edu/linux/checkstyle-{algs4,cos226,coursera}"
unzip checkstyle.zip

rm *.zip

cd ..

mkdir -p ~/usr/local
unlink $ALGS4ROOT/env/algs4
ln -s $ALGS4ROOT/env/algs4 ~/usr/local/

mkdir -p ~/usr/bin
unlink $ALGS4ROOT/env/bin/check*
unlink $ALGS4ROOT/env/bin/drjava
unlink $ALGS4ROOT/env/bin/findbugs*
unlink $ALGS4ROOT/env/bin/java*
unlink $ALGS4ROOT/env/bin/pmd*
ln -s $ALGS4ROOT/env/bin/* ~/usr/bin/

unlink ~/.drjava
ln -s $ALGS4ROOT/env/.drjava ~
