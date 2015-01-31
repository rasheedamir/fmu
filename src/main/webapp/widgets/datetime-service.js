'use strict';

angular.module('fmuClientApp').factory('DatetimeService', [function(){
	var startDate = new Date();
	var endDate = new Date();
    endDate.setMonth(startDate.getMonth() + 1);
    startDate.setMonth(startDate.getMonth() - 1);
    
	return {
		startDate: startDate,
		endDate: endDate
	};
}]);