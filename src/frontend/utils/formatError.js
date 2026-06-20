export function formatError(err) {
  try {
    const parsed = JSON.parse(err.message);
    return parsed.message || parsed.error || err.message;
  } catch {
    return err.message || 'Something went wrong';
  }
}
