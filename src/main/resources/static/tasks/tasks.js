angular.module('app').controller('taskController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8180/app';



    $scope.getTasks = function () {
        $http({
            url: contextPath + '/api/v1/tasks',
            method: 'GET',
            params: {
                title           : $scope.filter ? $scope.filter.title : null,
                producerId      : $scope.filter ? $scope.filter.producerId : null,
                responsibleId   : $scope.filter ? $scope.filter.responsibleId : null,
                taskStateId     : $scope.filter ? $scope.filter.taskStateId : null,
                taskStatesId    : $scope.filter ? $scope.filter.taskStatesId : null,
                coExecutorId    : $scope.filter ? $scope.filter.coExecutorId : null,
                executorId      : $localStorage.currentUser.profileId
            }

        }).then(function (response) {
            $scope.allTasks = response.data
        });
    };

    $scope.cleanFilter = function() {
        $scope.filter = null;
        $scope.allTasks();
    }

    $scope.createNew = function(){
        alert('В разработке');
    }

    $scope.getProfiles = function(){
        $http({
            url: contextPath + '/api/v1/profiles/mini',
            method: 'GET'
        }).then(function (response) {
            $scope.allProfiles = response.data
        });
    }

    $scope.getTaskStates = function(){
            $http({
                url: contextPath + '/api/v1/taskstates',
                method: 'GET'
            }).then(function (response) {
                $scope.allTaskStates = response.data
            });
        }

    $scope.getProfiles();
    $scope.getTaskStates();
    $scope.getTasks();

});
