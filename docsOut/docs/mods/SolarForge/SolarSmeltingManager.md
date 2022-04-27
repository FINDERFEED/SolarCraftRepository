# InfusingManager



## Importing the class

It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import at the very top of the file.
```zenscript
import mods.solarforge.InfusingManager;
```


## Implemented Interfaces
InfusingManager implements the following interfaces. That means all methods defined in these interfaces are also available in InfusingManager

- [IRecipeManager](/vanilla/api/recipe/manager/IRecipeManager)&lt;[InfusingRecipe](/mods/SolarForge/Recipe/InfusingRecipe)&gt;

## Methods

:::group{name=addJsonRecipe}

Return Type: void

```zenscript
InfusingManager.addJsonRecipe(name as string, mapData as MapData) as void
```

| Parameter | Type | Description |
|-----------|------|-------------|
| name | string | No Description Provided |
| mapData | [MapData](/vanilla/api/data/MapData) | No Description Provided |


:::

:::group{name=addRecipe}

Adds a new recipe to the Infuser Multiblock Setup

Return Type: void

```zenscript
InfusingManager.addRecipe(name as string, output as IItemStack, ings as IItemStack[][], catalysts as Block[][], processingTime as int, fragment as string, costs as RunicEnergyCost, tag as string) as void
```

| Parameter | Type | Description | Optional | DefaultValue |
|-----------|------|-------------|----------|--------------|
| name | string |  | false |  |
| output | [IItemStack](/vanilla/api/item/IItemStack) |  | false |  |
| ings | [IItemStack](/vanilla/api/item/IItemStack)[][] |  | false |  |
| catalysts | [Block](/vanilla/api/block/Block)[][] |  | false |  |
| processingTime | int |  | false |  |
| fragment | string |  | false |  |
| costs | [RunicEnergyCost](/mods/SolarForge/Type/RunicEnergyCost) |  | false |  |
| tag | string |  | true |  |


:::

:::group{name=getAllRecipes}

Return Type: stdlib.List&lt;T&gt;

```zenscript
// InfusingManager.getAllRecipes() as stdlib.List<T>

<recipetype:solarforge:infusing_new>.getAllRecipes();
```

:::

:::group{name=getRecipeByName}

Return Type: T

```zenscript
InfusingManager.getRecipeByName(name as string) as T
```

| Parameter | Type | Description |
|-----------|------|-------------|
| name | string | No Description Provided |


:::

:::group{name=getRecipeMap}

Return Type: T[[ResourceLocation](/vanilla/api/resource/ResourceLocation)]

```zenscript
// InfusingManager.getRecipeMap() as T[ResourceLocation]

<recipetype:solarforge:infusing_new>.getRecipeMap();
```

:::

:::group{name=getRecipesByOutput}

Return Type: stdlib.List&lt;T&gt;

```zenscript
InfusingManager.getRecipesByOutput(output as IIngredient) as stdlib.List<T>
```

| Parameter | Type | Description |
|-----------|------|-------------|
| output | [IIngredient](/vanilla/api/ingredient/IIngredient) | No Description Provided |


:::

:::group{name=remove}

Return Type: void

```zenscript
InfusingManager.remove(output as IIngredient) as void
```

| Parameter | Type | Description |
|-----------|------|-------------|
| output | [IIngredient](/vanilla/api/ingredient/IIngredient) | No Description Provided |


:::

:::group{name=removeAll}

Return Type: void

```zenscript
// InfusingManager.removeAll() as void

<recipetype:solarforge:infusing_new>.removeAll();
```

:::

:::group{name=removeByInput}

Return Type: void

```zenscript
InfusingManager.removeByInput(input as IItemStack) as void
```

| Parameter | Type | Description |
|-----------|------|-------------|
| input | [IItemStack](/vanilla/api/item/IItemStack) | No Description Provided |


:::

:::group{name=removeByModid}

Return Type: void

```zenscript
InfusingManager.removeByModid(modid as string, exclude as Predicate<string>) as void
```

| Parameter | Type | Description | Optional | DefaultValue |
|-----------|------|-------------|----------|--------------|
| modid | string | No Description Provided | false |  |
| exclude | Predicate&lt;string&gt; | No Description Provided | true | (name as string) as bool => false |


:::

:::group{name=removeByName}

Return Type: void

```zenscript
InfusingManager.removeByName(name as string) as void
```

| Parameter | Type | Description |
|-----------|------|-------------|
| name | string | No Description Provided |


:::

:::group{name=removeByRegex}

Return Type: void

```zenscript
InfusingManager.removeByRegex(regex as string, exclude as Predicate<string>) as void
```

| Parameter | Type | Description | Optional | DefaultValue |
|-----------|------|-------------|----------|--------------|
| regex | string | No Description Provided | false |  |
| exclude | Predicate&lt;string&gt; | No Description Provided | true | (name as string) as bool => false |


:::


## Properties

| Name | Type | Has Getter | Has Setter | Description |
|------|------|------------|------------|-------------|
| allRecipes | stdlib.List&lt;T&gt; | true | false | No Description Provided |
| recipeMap | T[[ResourceLocation](/vanilla/api/resource/ResourceLocation)] | true | false | No Description Provided |

