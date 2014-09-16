/**
 * Created by quavu on 2014-09-09.
 */

// Page size selection filter
fmuApp.filter('dateFilter', function(){
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