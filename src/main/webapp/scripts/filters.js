'use strict';

// Page size selection filter
angular.module('fmuClientApp').filter('dateFilter', function(){
    return function(items,datekey, startDate, endDate){
        var filtered = [];
        angular.forEach(items, function(item){
            if(item[datekey] >= startDate && item[datekey] <= endDate){
                filtered.push(item);
            }
        });

        return filtered;
    };
});