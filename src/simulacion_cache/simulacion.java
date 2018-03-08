
package simulacion_cache;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
/**
 *
 * @author Alexy Cruz Jordi Mairena Juan O'Hara Oscar Diaz
 */
public class simulacion {

    int linea_cache = 64;
    int tam_bloque = 8;
    int conj_cache = 16;
    int tam_conjunto = 4;
    int valido = 1;
    int modif = 1;
    int invalido = -1;
    int no_modif = -1;
    int dir_ram;
    double tiempo;
    int next;
    int cache[][] = new int[linea_cache][3];
    int cacheConjuntos[][][] = new int[conj_cache][tam_conjunto][3];
    int nextConjunto[][] = new int[conj_cache][1];
    int RAM[];
    String resultados = "";

    public simulacion() {
        dir_ram = 4096;

    }

    public simulacion(int direcciones) {
        dir_ram = direcciones;
    }

    private int[] crearArreglo() {
        final int arreglo[] = new int[dir_ram];
        InputStream in = getClass().getResourceAsStream("data.txt");
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
            for (int i = 0; i < dir_ram; i++) {
                arreglo[i] = Integer.parseInt(buffer.readLine());
            }
            in.close();
            buffer.close();
        } catch (FileNotFoundException erro) {
            System.out.println("No se encuentra el archivo.");
        } catch (IOException err) {
            System.out.println("Error al leer el archivo.");
        }
        return arreglo;
    }

    private void restaurarCache() {
        for (int i = 0; i < linea_cache; i++) {
            cache[i][0] = -1;
            cache[i][1] = -1;
            cache[i][2] = -1;
        }
        for (int i = 0; i < conj_cache; i++) {
            for (int j = 0; j < tam_conjunto; j++) {
                cacheConjuntos[i][j][0] = -1;
                cacheConjuntos[i][j][1] = -1;
                cacheConjuntos[i][j][2] = -1;
            }
        }
    }

    private int estaEnCache(int bloque) {
        for (int i = 0; i < linea_cache; i++) {
            if (cache[i][2] == bloque) {
                return i;
            }
        }
        return -1;
    }

    private int estaEnConjunto(int conjunto, int etiqueta) {
        for (int i = 0; i < tam_conjunto; i++) {
            if (cacheConjuntos[conjunto][i][2] == etiqueta) {
                return i;
            }
        }
        return -1;
    }

    private int leer(int direccion, int tipo) {
        switch (tipo) {
            case 0:
                tiempo += 0.1;
                break;
                //directa
            case 1: {
                final int bloque = direccion / tam_bloque;
                final int linea = bloque % linea_cache;
                final int etiqueta = bloque / linea_cache;

                if (cache[linea][0] == invalido) {
                    cache[linea][0] = valido;
                    cache[linea][1] = no_modif;
                    cache[linea][2] = etiqueta;
                    tiempo += 0.11;
                } else {
                    if (cache[linea][2] == etiqueta) {
                        tiempo += 0.01;
                    } else {
                        if (cache[linea][1] == modif) {
                            cache[linea][1] = no_modif;
                            tiempo += 0.22;
                        } else {
                            tiempo += 0.11;
                        }
                        cache[linea][2] = etiqueta;
                    }
                }
            }
            break;

            //asociativo
            case 2: {
                if (next > linea_cache - 1) {
                    next = 0;
                }
                final int bloque = direccion / tam_bloque;

                if (estaEnCache(bloque) != -1) {
                    tiempo += 0.01;
                    break;
                }

                final int linea = next;
                final int etiqueta = bloque;

                if (cache[linea][0] == invalido) {
                    cache[linea][0] = valido;
                    cache[linea][1] = no_modif;
                    cache[linea][2] = etiqueta;
                    next++;
                    tiempo += 0.11;
                } else {
                    if (cache[linea][1] == modif) {
                        cache[linea][1] = no_modif;
                        tiempo += 0.22;
                    } else {
                        tiempo += 0.11;
                    }
                    cache[linea][2] = etiqueta;
                    next++;
                }
            }
            break;

            //asociativo por conjuntos
            case 3: {
                final int bloque = direccion / tam_bloque;
                final int conjunto = bloque % conj_cache;
                final int etiqueta = bloque / conj_cache;

                if (nextConjunto[conjunto][0] > tam_conjunto - 1) {
                    nextConjunto[conjunto][0] = 0;
                }

                if (estaEnConjunto(conjunto, etiqueta) != -1) {
                    tiempo += 0.01;
                    break;
                }

                int linea = nextConjunto[conjunto][0];

                if (cacheConjuntos[conjunto][linea][0] == invalido) {
                    cacheConjuntos[conjunto][linea][0] = valido;
                    cacheConjuntos[conjunto][linea][1] = no_modif;
                    cacheConjuntos[conjunto][linea][2] = etiqueta;
                    nextConjunto[conjunto][0]++;
                    tiempo += 0.11;
                } else {
                    if (cacheConjuntos[conjunto][linea][1] == modif) {
                        cacheConjuntos[conjunto][linea][1] = no_modif;
                        tiempo += 0.22;
                    } else {
                        tiempo += 0.11;
                    }
                    cacheConjuntos[conjunto][linea][2] = etiqueta;
                    nextConjunto[conjunto][0]++;
                }
            }
            break;

            default:
                break;
        }

        return RAM[direccion];
    }

    private void escribir(int direccion, int tipo, int dato) {
        switch (tipo) {
            case 0:
                tiempo += 0.1;
                break;

            case 1: {
                final int bloque = direccion / tam_bloque;
                final int linea = bloque % linea_cache;
                final int etiqueta = bloque / linea_cache;

                if (cache[linea][0] == invalido) {
                    cache[linea][0] = valido;
                    cache[linea][2] = etiqueta;
                    tiempo += 0.11;
                } else {
                    if (cache[linea][2] == etiqueta) {
                        tiempo += 0.01;
                    } else {
                        if (cache[linea][1] == modif) {
                            tiempo += 0.22;
                        } else {
                            tiempo += 0.11;
                        }
                        cache[linea][2] = etiqueta;
                    }
                }
                
                cache[linea][1] = modif;
            }
            break;

            case 2: {
                if (next > linea_cache - 1) {
                    next = 0;
                }
                final int bloque = direccion / tam_bloque;
                final int etiqueta = bloque;
                int linea;

                if ((linea = estaEnCache(bloque)) != -1) {
                    tiempo += 0.01;
                    cache[linea][1] = modif;
                    break;
                }
                linea = next;
                if (cache[linea][0] == invalido) {
                    cache[linea][0] = valido;
                    cache[linea][2] = etiqueta;
                    tiempo += 0.11;
                } else {
                    if (cache[linea][1] == modif) {
                        tiempo += 0.22;
                    } else {
                        tiempo += 0.11;
                    }
                    cache[linea][2] = etiqueta;
                }
                next++;
            }
            break;

            case 3: {
                final int bloque = direccion / tam_bloque;
                final int conjunto = bloque % conj_cache;
                final int etiqueta = bloque / conj_cache;

                if (nextConjunto[conjunto][0] > tam_conjunto - 1) {
                    nextConjunto[conjunto][0] = 0;
                }

                int linea;
                if ((linea = estaEnConjunto(conjunto, etiqueta)) != -1) {
                    tiempo += 0.01;
                    cacheConjuntos[conjunto][linea][1] = modif;
                    break;
                }

                linea = nextConjunto[conjunto][0];

                if (cacheConjuntos[conjunto][linea][0] == invalido) {
                    cacheConjuntos[conjunto][linea][0] = valido;
                    cacheConjuntos[conjunto][linea][2] = etiqueta;
                    tiempo += 0.11;
                } else {
                    if (cacheConjuntos[conjunto][linea][1] == modif) {
                        tiempo += 0.22;
                    } else {
                        tiempo += 0.11;
                    }
                }
                nextConjunto[conjunto][0]++;
            }
            break;

            default:
                break;
        }

        RAM[direccion] = dato;
    }

    private void ordenar(int tipo) {
        for (int i = 0; i <= dir_ram - 2; i++) {
            for (int j = i + 1; j <= dir_ram - 1; j++) {
                if (leer(i, tipo) > leer(j, tipo)) {
                    int temp = leer(i, tipo);
                    escribir(i, tipo, leer(j, tipo));
                    escribir(j, tipo, temp);
                }
            }
        }
    }

    public void correr() {
        final int DATOS[] = crearArreglo();
        final String TIPOS[] = new String[]{"Sin cache", "Directa", "Asociativa", "Asociativa por conjuntos"};
        resultados = String.format("Resultados (N = %d)%n%n", dir_ram);
        for (int tipo = 0; tipo < 4; tipo++) {
            RAM = Arrays.copyOf(DATOS, dir_ram);
            restaurarCache();
            tiempo = 0;

            ordenar(tipo);
            resultados += String.format("%s:%n%,.2f Î¼s%n%n", TIPOS[tipo], tiempo);
        }
    }
}
