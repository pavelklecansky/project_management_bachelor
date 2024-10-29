export const getAvatarUrl: (firstName: string, lastName: string) => string = (
	firstName: string,
	lastName: string
) => {
	return `https://api.dicebear.com/7.x/bottts-neutral/svg?seed=${firstName}-${lastName}`;
};
