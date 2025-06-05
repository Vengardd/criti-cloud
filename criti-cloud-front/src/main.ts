import { createApp } from "vue";
import App from "./App.vue";
import { router } from "./router";
import { VueQueryPlugin } from "@tanstack/vue-query";
import "./assets/tailwind.css";

createApp(App).use(router).use(VueQueryPlugin).mount("#app");
