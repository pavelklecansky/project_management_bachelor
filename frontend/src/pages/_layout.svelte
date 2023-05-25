<script>
    import Header from "../components/header/Header.svelte";
    import Sidebar from "../components/sidebar/Sidebar.svelte";
    import {goto} from "@roxi/routify";
    import {authState} from "../lib/auth";
    import {hidden} from "../lib/nav";
    import {onMount} from "svelte";

    $: outerWidth = 0;

  onMount(() => {
    if (outerWidth < 1024) {
      $hidden = true;
    } else {
      $hidden = false;
    }
  });

  $: if ($hidden) {
    if (outerWidth >= 1024) {
      $hidden = false;
    }
  }
</script>

<svelte:window bind:outerWidth />
<div
  on:click={() => ($hidden = true)}
  class="{$hidden
    ? 'hidden'
    : 'block'} fixed inset-0 top-0 left-0 w-screen h-screen z-20 transition-opacity bg-black opacity-50 lg:hidden"
/>

{#if $authState.isSignedIn}
  <div id="app" class="flex h-screen flex-wrap">
    <div class="lg:w-2/12 overflow-hidden">
      <Sidebar />
    </div>
    <section class="flex flex-col flex-1 lg:w-10/12 w-full overflow-hidden">
      <Header />
      <main class="px-6 mt-2 mb-16 h-full">
        <slot />
      </main>
      <footer />
    </section>
  </div>
{:else}
  {$goto("/authentication") || ""}
{/if}
