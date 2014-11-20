'use strict';

angular.module('fmuClientApp')
.factory('AuthService', ['$resource', 'RESTURL', '$http', function($resource, RESTURL, $http){
    var userInfo = {};
    
    var UserResource = $resource(RESTURL.userInfo);
    
    function getUserInfo(){
    	userInfo = UserResource.get();
    	return userInfo;
    }

    function hasRole(roleName){
        return userInfo.activeRole == roleName;
    }
    
    function changeRole(roleName){
    	$http.put(RESTURL.changeRole, null, {params: {role: roleName}})
    }

    return {
        hasRole: hasRole,
        getUserInfo: getUserInfo,
        changeRole: changeRole
    };
}]);
