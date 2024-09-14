export interface CapacitorWebviewUpdatePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
