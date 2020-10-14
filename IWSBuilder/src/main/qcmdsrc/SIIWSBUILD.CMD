             CMD        PROMPT('(SI) IWS Builder')

             PARM       KWD(IWSSSTMF) TYPE(*CHAR) LEN(256) CASE(*MIXED) PROMPT('IWSS Streamfile')
             PARM       KWD(JARFILE) TYPE(*CHAR) LEN(256) CASE(*MIXED) PROMPT('jar file with si-iws-builder') DFT('/home/CECUSER/si-iws-builder/IWSBuilder/target/si-iws-builder-0.0.1-SNAPSHOT-jar-with-dependencies.jar')