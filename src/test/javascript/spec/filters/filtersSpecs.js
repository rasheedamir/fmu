'use strict';

describe('Filters Tests ', function () {
    var $filter;

    beforeEach(function () {
        module('fmuApp');

        inject(function (_$filter_) {
            $filter = _$filter_;
        });
    });
});
