
angular.module('fmuClientApp')
.factory('AuthService', function(){
    var userInfo = {roles: []};

    function hasRole(roleName){
        return userInfo.roles.indexOf(roleName) != -1;
    }

    function removeRole(roleName){
        userInfo.roles.splice(userInfo.roles.indexOf(roleName), 1);
    }

    function addRole(roleName){
        userInfo.roles.push(roleName);
    }

    return {
        userInfo: userInfo,
        hasRole: hasRole,
        removeRole: removeRole,
        addRole: addRole
    };
});
