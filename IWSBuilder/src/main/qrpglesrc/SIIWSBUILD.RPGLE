**FREE

ctl-opt main(SIIWSBUILD);

/define HELLOWORLD
/include qcpylesrc/SIIWSBUILDP.rpgle

dcl-proc SIIWSBUILD;
dcl-pi SIIWSBUILD;
  iwssstmf like(tIWSSStmf) const;
end-pi;

  return;
end-proc;