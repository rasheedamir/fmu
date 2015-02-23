(function() {
    'use strict';
    angular.module('fmu.core')
        .controller('FakeRoleCtrl', FakeRoleCtrl);
    /*@ngInject*/
    function FakeRoleCtrl($scope, AuthService) {
        $scope.roles = ['ROLE_SAMORDNARE', 'ROLE_UTREDARE'];
        $scope.hasRole = AuthService.hasRole;
        $scope.changeRole = AuthService.changeRole;
    }
    FakeRoleCtrl.$inject = ['$scope', 'AuthService'];
})();