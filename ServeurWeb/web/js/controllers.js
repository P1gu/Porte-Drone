'use strict';

/* Controllers */

angular.module('myApp.controllers', [])
        .controller('MyCtrl1', [function() {

            }])
        .controller('CtrlVols', ['$scope', '$http', function($scope, $http) {

                $http({method: 'GET', url: '/ServeurWeb/Index?action=lesVols'}).
                        success(function(data, status, headers, config) {
                            $scope.lesVols = data;
                        });
            }]);
