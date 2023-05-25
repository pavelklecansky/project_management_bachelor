
export const getAvatarUrl: (firstName: string, lastName: string) => string = (firstName: string, lastName: string) => {
    return `https://avatars.dicebear.com/v2/initials/${firstName}-${lastName}.svg`;
}