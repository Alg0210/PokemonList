export function formatSpeciesName(species) {
  if (!species) return '';
  return species.replace(/-/g, ' ');
}
