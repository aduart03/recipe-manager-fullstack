import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Recipes } from './components/recipes/recipes';
import { About } from './components/about/about';

export const routes: Routes = [

    {path : '', component: Home},
    {path : 'recipes', component: Recipes},
    {path : 'about', component: About}
];
