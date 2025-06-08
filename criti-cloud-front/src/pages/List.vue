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
  <div class="p-4 max-w-md mx-auto">
    <h1 class="text-2xl font-bold mb-4">Lista filmów</h1>

    <div v-if="isLoading" class="text-center py-10">Ładowanie...</div>

    <ul v-else class="space-y-4 overflow-y-auto pb-4">
      <li v-for="item in data" :key="item.mediaId" class="flex space-x-4">
        <div
          class="bg-gray-300 flex items-center justify-center rounded overflow-hidden flex-shrink-0"
        >
          <!-- Placeholder obrazka -->
          <img :src="item.imgUrl" width="100px" />
        </div>

        <div class="flex flex-col ms-4">
          <h2 class="font-semibold text-lg mb-3">{{ item.mediaName }}</h2>
          <p
            v-if="item.mediaDescription"
            class="text-gray-600 text-sm line-clamp-5 max-w-md"
          >
            {{ item.mediaDescription }}
          </p>
          <p v-else class="text-gray-600 text-sm line-clamp-5 max-w-md">
            Opis dla tego filmu nie jest dostępny.
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
    mediaName: "Gra o tron",
    detailsType: "movie",
    detailsId: 101,
    mediaImage: null,
    mediaDescription: "Krótki opis filmu",
    imgUrl:
      "https://m.media-amazon.com/images/M/MV5BNGYxOGJkMjItZjVkZC00OGEzLWExNjktOTZmNGZhZmRlMTk2XkEyXkFqcGc@._V1_.jpg",
  },
  {
    mediaId: 2,
    mediaName: "Incepcja",
    detailsType: "movie",
    detailsId: 102,
    mediaImage: null,
    mediaDescription:
      "Krótki opis filmu Incepcja z dłuższym tekstem, który powinien się ładnie przycinać.",
    imgUrl:
      "https://m.media-amazon.com/images/M/MV5BZjhkNjM0ZTMtNGM5MC00ZTQ3LTk3YmYtZTkzYzdiNWE0ZTA2XkEyXkFqcGc@._V1_.jpg",
  },
  {
    mediaId: 3,
    mediaName: "Interstellar",
    detailsType: "movie",
    detailsId: 103,
    mediaImage: null,
    mediaDescription: "Opis filmu Interstellar z krótkim tekstem.",
    imgUrl: "https://fwcdn.pl/fpo/56/29/375629/7670122_2.5.jpg",
  },
  {
    mediaId: 4,
    mediaName: "Teściowie",
    detailsType: "movie",
    detailsId: 102,
    mediaImage: null,
    mediaDescription: "",
    imgUrl:
      "https://m.media-amazon.com/images/M/MV5BY2I1N2QyOTktNjRhNi00MThiLWFhYTYtZjcwYzBkN2ZiZjU1XkEyXkFqcGc@._V1_.jpg",
  },
]);

const isLoading = ref(false);

// Docelowo tutaj fetch z vue-query, np:
// const { data, isLoading } = useQuery(...);
</script>
