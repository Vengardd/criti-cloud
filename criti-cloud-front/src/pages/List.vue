<template>
  <div>
    <h1 class="text-xl font-bold">Lista elementów</h1>
    <div v-if="isLoading">Ładowanie...</div>
    <ul v-else>
      <li v-for="item in data" :key="item.id">
        {{ item.name }}
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { useQuery } from "@tanstack/vue-query";

const fetchItems = async () => {
  const res = await fetch("https://api.example.com/items");
  if (!res.ok) throw new Error("Fetch failed");
  return res.json();
};

const { data, isLoading } = useQuery({
  queryKey: ["items"],
  queryFn: fetchItems,
});
</script>
