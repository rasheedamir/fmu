'use strict';
angular.module('fmuClientApp').
directive('enableSelectionHighlight', [function() {
        return {
            restrict: 'A',
            link: function($scope, iElm, iAttrs) {
                var selectionClass = iAttrs.enableSelectionHighlight;
                var tabs = iElm.find('li');
                $scope.$watch($scope.$last, function() {
                    tabs = iElm.find('li');
                    var defaultSelection = iAttrs.highlightTab - 1;
                    if (tabs.eq(defaultSelection)) {
                        tabs.eq(defaultSelection).addClass(selectionClass);
                    }
                });

                iElm.on('click', 'li', function() {
                    if (selectionClass !== null) {
                        angular.forEach(tabs, function(element) {
                            angular.element(element).removeClass(selectionClass);
                        });
                        angular.element(this).toggleClass(selectionClass);
                    }
                });
            }
        };
    }
]);