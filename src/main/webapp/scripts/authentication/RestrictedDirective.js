angular.module('fmuClientApp')
.directive('restricted', ['AuthService', function(AuthService){
    return {
        restrict: 'A',
        link: function($scope, elm, attrs){
            var role = attrs.role;
            var oldcss = elm.css('display');

            $scope.userInfo = AuthService.userInfo;


            function updateCSS(){
                if(AuthService.hasRole(role)){
                    elm.css('display', oldcss);
                } else {
                    elm.css('display', 'none');
                }
            }
            $scope.$watch('userInfo', function(n,o){
                updateCSS();
            }, true);

        }
    };
}]);
