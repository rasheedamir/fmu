(function() {
    'use strict';
    angular.module('fmu.core')
        .controller('FakeRoleCtrl', FakeRoleCtrl);
    /*@ngInject*/
    function FakeRoleCtrl($scope, AuthService) {
        $scope.roles = ['ROLE_SAMORDNARE', 'ROLE_UTREDARE'];
        $scope.hasRole = AuthService.hasRole;
        $scope.changeRole = changeRoleFn;


        function changeRoleFn(role) {
            AuthService.changeRole(role).then(function() {
                location.reload();
            });
        }
    }
    FakeRoleCtrl.$inject = ['$scope', 'AuthService'];
})();