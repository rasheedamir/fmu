(function() {
    'use strict';

    angular.module('fmu.core')

        .factory('AuthService', authService);

    /*@ngInject*/
    function authService($resource, $http, AUTH) {
        var userInfo = {};
        var UserResource = $resource(AUTH.userInfo);

        return {
            hasRole: hasRole,
            getUserInfo: getUserInfo,
            changeRole: changeRole
        };

        function getUserInfo() {
            userInfo = UserResource.get();
            return userInfo;
        }

        function hasRole(roleName) {
            return userInfo.activeRole === roleName;
        }

        function changeRole(roleName) {
            $http.put(AUTH.changeRole, null, {
                params: {
                    role: roleName
                }
            });
        }
    }
    authService.$inject = ['$resource', '$http', 'AUTH'];
})();