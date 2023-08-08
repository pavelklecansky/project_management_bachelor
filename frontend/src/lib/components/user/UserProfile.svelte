<script lang="ts">
    import {authState} from "$lib/auth";
    import {getAvatarUrl} from "$lib/avatar";

    $: user = $authState.user;
    $: fullName = $authState.user.firstName + " " + $authState.user.lastName;
</script>

<div class="bg-white border border-gray-100 rounded">
    <header class="flex items-stretch border-b border-gray-100">
        <p class="flex items-center py-3 px-4 flex-grow font-bold">Profile</p>
    </header>
    <div class="p-6">
        <div class="image w-48 h-48 mx-auto">
            <img
                src={getAvatarUrl(user.firstName, user.lastName)}
                alt={fullName}
                class="rounded-full"
            />
        </div>
        <hr />
        <div class="mb-3">
            <p class="block font-bold mb-2">Name</p>
            <div>{fullName}</div>
        </div>
        <hr />
        <div class="mb-3">
            <p class="block font-bold mb-2">E-mail</p>
            <div class="control">{user.email}</div>
        </div>
        <div class="mb-3">
            <p class="block font-bold mb-2">Phone number</p>
            <div class="control">{user.phoneNumber}</div>
        </div>
        {#if user.organizations}
        <div class="mb-3">
            <p class="block font-bold mb-2">Organizations</p>
            <div class="control">{user.organizations.map(organization => organization.name).join(", ")}</div>
        </div>
        {/if}
        <div class="mb-3">
            <p class="block font-bold mb-2">Note</p>
            <div class="control">{user.note}</div>
        </div>
    </div>
</div>
