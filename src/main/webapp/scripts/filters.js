/**
 * Created by quavu on 2014-09-09.
 */

// Page size selection filter
fmuApp.filter('pageCount', function(){
    return function(input, totalItems){
        var pattern = [10, 20, 50, 100];
        for(var i = 0; i < pattern.length; i++){
            if(totalItems < pattern[i]){
                input.push(pattern[i]);
                break;
            }
            input.push(pattern[i]);
        }
        if(totalItems > pattern[pattern.length - 1]){
            input.push(totalItems);
        }
        return input;
    };
});

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