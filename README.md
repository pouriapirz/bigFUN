# AsterixDB BigFUN Client

[__BigFUN__](http://ieeexplore.ieee.org/xpl/articleDetails.jsp?arnumber=7363793 "BigFUN") is a micro-benchmark to compare Big Data management systems w.r.t their features and functionality.
Here you can find the client to run BigFUN against AsterixDB for both read-only and data modification (update) workloads. You can generate the data for running BigFUN using [__SocialGen__](https://github.com/pouriapirz/socialGen "SocialGen"). 
The DDL to prepare an AsterixDB instance for running BigFUN can be found [here](https://github.com/pouriapirz/bigFUN/blob/master/files/ddl/ddl.aql).
The following table lists the BigFUN operations. The operation-id is used in the client configuration files to create BigFUN workloads. 
The AQL templates for the queries can be found [here](https://github.com/pouriapirz/bigFUN/tree/master/files/queries).

  Operation-id | Description |
  --- | --- |
  *101* | Unique record retrieval (PK lookup) |
  *102* | Record id range scan (PK scan) |
  *103* | Temporal range scan (Non-unique attribute scan) |
  *104* | Existential quantification |
  *105* | Universal quantification |
  *106* | Global aggregation |
  *107* | Grouping & aggregation |
  *108* | Top-K |
  *109* | Spatial selection |
  *1010* | Text containment search |
  *1011* | Text similarity search |
  *1012* | Select equi-join |
  *2012* | Select indexed equi-join |
  *1013* | Select left-outer equi-join |
  *2013* | Select indexed left-outer equi-join |
  *1014* | Select join with grouping & aggregation |
  *2014* | Select indexed join with grouping & aggregation |
  *1015* | Select join with Top-K |
  *2015* | Select indexed join with Top-K |
  *1016* | Spatial join |
  *insert* | (Batch) record addition |
  *delete* | (Batch) record removal |

## Prerequisites
* A suitable *nix environment (Linux, OSX)
* JDK 1.8+
* Maven 3.1.1 or greater

