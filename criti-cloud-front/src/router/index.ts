import { createRouter, createWebHistory } from "vue-router";
import List from "@/pages/List.vue";

const routes = [{ path: "/", component: List }];

export const router = createRouter({
  history: createWebHistory(),
  routes,
});
