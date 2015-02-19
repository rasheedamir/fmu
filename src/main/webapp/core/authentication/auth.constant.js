'use strict';

angular.module('fmu.core')
    .constant('AUTH', {
        userInfo: 'app/rest/user',
        changeRole: 'app/rest/user/changerole'
    });