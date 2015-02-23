(function() {
    'use strict';
    angular.module('fmu.widgets')
        .directive('restricted', restricted);

    /*@ngInject*/
    function restricted(AuthService) {
        return {
            restrict: 'A',
            link: function($scope, elm, attrs) {
                var role = attrs.role;
                var oldcss = elm.css('display');

                $scope.userInfo = AuthService.getUserInfo();
                $scope.$watch('userInfo', function() {
                    updateCSS();
                }, true);

                function updateCSS() {
                    if (AuthService.hasRole(role)) {
                        elm.css('display', oldcss);
                    } else {
                        elm.css('display', 'none');
                    }
                }
            }
        };
    }
    restricted.$inject = ['AuthService'];
})();