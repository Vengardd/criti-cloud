<!-- <template>
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
</script> -->
<template>
  <div class="p-4 mx-auto">
    <h1 class="text-2xl font-bold mb-4">Lista gier</h1>

    <div v-if="isLoading" class="text-center py-10">Ładowanie...</div>

    <ul v-else class="space-y-4 max-h-[400px] overflow-y-auto pb-4">
      <li
        v-for="item in data"
        :key="item.mediaId"
        class="flex space-x-4 py-4 ps-4 border rounded shadow-sm hover:shadow-md transition"
      >
        <div
          class="bg-gray-300 flex items-center justify-center rounded overflow-hidden flex-shrink-0"
        >
          <!-- Placeholder obrazka -->
          <span class="text-gray-600 select-none">Image</span>
          <!-- Gdy będziesz mieć mediaImage, to użyj <img :src="item.mediaImage" alt="..." /> -->
        </div>

        <div class="flex flex-col justify-center">
          <h2 class="font-semibold text-lg">{{ item.mediaName }}</h2>
          <p class="text-gray-600 text-sm truncate max-w-xs">
            {{ item.mediaDescription }}
          </p>
        </div>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

// Mockowe dane (docelowo fetch)
interface GameItem {
  mediaId: number;
  mediaName: string;
  detailsType: string;
  detailsId: number;
  mediaImage: string | null;
  mediaDescription: string;
}

// Na start mock
const data = ref<GameItem[]>([
  {
    mediaId: 1,
    mediaName: "Gra 1",
    detailsType: "game",
    detailsId: 101,
    mediaImage: null,
    mediaDescription: "Krótki opis gry numer 1",
  },
  {
    mediaId: 2,
    mediaName: "Gra 2",
    detailsType: "game",
    detailsId: 102,
    mediaImage: null,
    mediaDescription:
      "Krótki opis gry numer 2 z dłuższym tekstem, który powinien się ładnie przycinać.",
  },
  {
    mediaId: 3,
    mediaName: "Gra 3",
    detailsType: "game",
    detailsId: 103,
    mediaImage: null,
    mediaDescription: "Opis gry numer 3",
  },
  {
    mediaId: 4,
    mediaName: "Gra 2",
    detailsType: "game",
    detailsId: 102,
    mediaImage: null,
    mediaDescription:
      "Krótki opis gry numer 2 z dłuższym tekstem, który powinien się ładnie przycinać.",
  },
]);

const isLoading = ref(false);

// Docelowo tutaj fetch z vue-query, np:
// const { data, isLoading } = useQuery(...);
</script>
