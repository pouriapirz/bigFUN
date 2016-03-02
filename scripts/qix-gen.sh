#!/bin/bash

PDIR=$1
OUTPUTFILE=$2

rm -f ${OUTPUTFILE}
echo -e "{" >> ${OUTPUTFILE}

for i in 101 102 103 104 105 106 107 108 109 1010 1011 1012 2012 1013 2013 1014 2014 1015 2015
do

QFile=${PDIR}/q${i}.aql 

if [ ! -f ${QFile} ]; then
    echo -e "WARNING: The query file for query $i can not be found at ${QFile} !"
fi

echo -e "\"${i}\" : \"${QFile}\"," >> ${OUTPUTFILE}
done

QFile=${PDIR}/q1016.aql
if [ ! -f ${QFile} ]; then
    echo "WARNING: The query file for query $i can not be found at ${QFile} !"
fi
echo -e "\"1016\" : \"${QFile}\"" >> ${OUTPUTFILE}
echo -e "}" >> ${OUTPUTFILE}
