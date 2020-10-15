**FREE

ctl-opt main(helloworld);
ctl-opt pgminfo(*pcml:*module:*dclcase);

/define HELLOWORLD
/include qcpylesrc/helloworldp.rpgle

dcl-proc HelloWorld ;
dcl-pi HelloWorld;
  Name like(tName) const;
  Response like(tJSON);
  ResponseCode like(tHttpStatus);
end-pi;

  ResponseCode = 200; // OK

  if Name = *blanks;
    ResponseCode = 400; // Bad Request
    return;
  endif;

  exec sql set :REsponse = json_object(key 'greeting' value 'Hello ' concat trim(:name));
  if sqlcode <> 0;
    ResponseCode = 500; // Internal Server Error
    return;
  endif;

  return;
end-proc;
