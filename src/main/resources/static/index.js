(function () {
    'use strict';

    function materialTheme($mdThemingProvider) {
        var primaryPalette = $mdThemingProvider.extendPalette('light-blue', {
            '500': '3092ce',
            // '900': '0277bd',
            'contrastDefaultColor': 'light'
        });

        var backgroundPalette = $mdThemingProvider.extendPalette('grey', {
            '300': 'fff9c4',
            'contrastDefaultColor': 'dark'
        });

        $mdThemingProvider.definePalette('primaryPalette', primaryPalette);
        $mdThemingProvider.definePalette('backgroundPalette', backgroundPalette);

        $mdThemingProvider.theme('default')
            .primaryPalette('primaryPalette')
            .accentPalette('red')
            .backgroundPalette('backgroundPalette');
    }

    //Какие-то из модулей могут быть не нужны. Лишнее выпилим при рефакторинге
    angular
        .module('app', [
            'ngRoute',
            'ngMaterial',
            'ngMessages',
            'ngAria',
            'ngStorage',
            'angular-jwt',
            'ngAnimate',
            'ngSanitize',
            'ui.bootstrap',
            'ngStomp',
            'chatService',
            'pageService'
        ])
        .config(config)
        .config(['$mdThemingProvider', materialTheme])
        .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'main/main.html'
            })
            .when('/auth', {
                templateUrl: 'auth/auth.html',
                controller: 'authController'
            })
            .when('/companies', {
                templateUrl: 'company/companies.html',
                controller: 'companyController'
            })
            .when('/companies/company', {
                templateUrl: 'company/company.html',
                controller: 'companyController'
            })
            .when('/profiles', {
                templateUrl: 'profile/profiles.html',
                controller: 'profilesController'
            })
            .when('/profiles/profile', {
                templateUrl: 'profile/profile.html',
                controller: 'profileController'
            })
            .when('/projects', {
                templateUrl: 'projects/projects.html',
                controller: 'projectController'
            })
            .when('/tasks', {
                templateUrl: 'tasks/tasks.html',
                controller: 'taskController'
            })
            .when('/chats', {
                templateUrl: 'chat/room.html',
                controller: 'roomController'
            })
            .when('/tasks/:taskId', {
               templateUrl: 'tasks/task.html',
               controller: 'taskEditController'
            });
    }

    //Функция проверяет наличие пользователя в локальном хранилище и клеит токен к заголовку
    function run($rootScope, $http, $location, $localStorage, chatService) {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;

            //Подключение к вебсокету при обновлении страницы
            chatService.ready = chatService.connect('/app/ws', {}, function (error) {
                alert(error);
            });
        }
        //Доступ без токена возможен только на страницы в массиве publicPages,
        // все остальное перенаправляется на /auth
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            let publicPages = ['/auth'];
            let restrictedPage = publicPages.indexOf($location.path()) === -1;
            if (restrictedPage && !$localStorage.currentUser) {
                $location.path('/auth');
            }
        });
    }
})();

angular.module('app').controller('indexController', function ($scope, $rootScope, $http, $location, $localStorage, $mdDialog, chatService) {

    const contextPath = 'http://localhost:8180/app';
    let isProfilePresent = false;
    $scope.currentNavItem = $location.path();

    $scope.isUserLoggedIn = function () {
        if ($localStorage.currentUser == null) {
            return false;
        } else {
            if (!isProfilePresent) {
                isProfilePresent = true;
                $http.get(contextPath + '/api/v1/profiles/profile')
                    .then(function (response) {
                        $rootScope.currentProfile = response.data;
                    });
            }
            return true;
        }
    };

    $scope.tryToLogout = function () {
        delete $localStorage.currentUser;
        $rootScope.currentProfile = null;
        isProfilePresent = false;
        $http.defaults.headers.common.Authorization = '';
        chatService.unsubscribe();
        chatService.disconnect();
        $location.path('/auth');
    };

    $scope.search = function () {
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.querySelector('#popupContainer')))
                .clickOutsideToClose(true)
                .title('Поиск')
                .textContent("Появится в этом апреле...")
                .ok('ОК')
        );
    }

    $scope.isUserLoggedIn = function () {
        if ($localStorage.currentUser) {
            $scope.currentUserName = $localStorage.currentUser.username;
            return true;
        } else {
            return false;
        }
    };

    $scope.currentNavItem = $location.path();
});
