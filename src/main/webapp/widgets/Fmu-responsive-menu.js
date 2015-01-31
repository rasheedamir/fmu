'use strict';
angular.module('fmuClientApp')
    .directive('fmuResponsiveMenu', ['$compile', function ($compile) {
        return {
            restrict: 'A',
            link: function (scope, iElement) {
                var element = angular.element('<div class="navigation-collapsed"><button ng-click="expandMenu()" class="fa fa-bars"></button></div>');
                var menuElement = $compile(element)(scope);
                iElement.prepend(menuElement);

                scope.expandMenu = function(){
                    var menus = iElement.find('.navigation-expanded');
                    var oldCss = menus.css('display');
                    if(oldCss === 'none'){
                        menus.css('display', 'block');
                    } else {
                        menus.css('display', 'none');
                    }
                };
            }
        };
    }]);