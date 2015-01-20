'use strict';
angular.module('fmuClientApp')
    .directive('enableSelectionHighlight', [ function () {
        return {
            restrict: 'C',
            link: function (scope, iElement) {
                var originColor = iElement.find('li').css('background-color');
                console.log(iElement.find('li').)
        }};
    }]);