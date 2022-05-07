
local val=redis.call('setnx',KEYS[1],'1')

if(val == 1) then
    redis.call('expire' , KEYS[1] , ARGV[1] )
    return true;
else
    return false;
end;